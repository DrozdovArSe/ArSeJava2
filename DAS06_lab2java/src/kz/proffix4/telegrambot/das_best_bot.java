package kz.proffix4.telegrambot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.pow;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class MyTelegramBot extends TelegramLongPollingBot {

    // Метод получения команд бота, тут ничего не трогаем
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(doCommand(update.getMessage().getChatId(),
                    update.getMessage().getText()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }
    }

    // Тут задается нужное значение имени бота
    @Override
    public String getBotUsername() {
        return "das_best_bot";
    }

    // Тут задается нужное значение токена
    @Override
    public String getBotToken() {
        return "5657576008:AAGS7iTH3HjJSPe088CRzJhIetb5WPYs1Sk";
    }

    // Метод обработки команд бота
    public String doCommand(long chatId, String command) {
        if (command.startsWith("/solve")) {
            try {
                sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("task.PNG")));
            } catch (TelegramApiException e) {
            }
            String[] param = command.split(" ");
            if (param.length > 1) { 
                return "Ответ: " + getResh(Integer.parseInt(param[1]),Integer.parseInt(param[2]),Integer.parseInt(param[3]));
            } else {
         return "Введите команду /solve и после неё значения a b x";
            }
        }
        return "Введите команду /solve и после неё значения a b x";
    }

    private String getResh(int a, int b, int x) {
        StringBuilder ans = new StringBuilder();
        double y = 0;
        if (x > 6) {
            y = (6*x*x-a*b)/(2*x*x);
        } else {
            y = 4*(x+a*a+b*b);
        }
        if (!Double.isNaN(y) && !Double.isInfinite(y)) { 
            ans.append(y); 
        } else {
            return "Деление на ноль запрещено!";
        }
        return ans.toString();
    }
}
public class das_best_bot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            // ЗАПУСКАЕМ КЛАСС НАШЕГО БОТА
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }

}
