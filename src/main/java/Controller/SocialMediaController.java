package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::verifyLogin);

        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        /*
         * app.post("/messages", this::)
         * 
         * 
         * 
         * app.patch("/messages/{message_id}", this::)
         * app.get("/accounts/{account_id}/messages", this::)
         */


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     *
    private void exampleHandler(Context context) {
        context.json("sample text");
    }*/

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        // if username is empty, password less than 4 long, and username found in database already, return error
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4 || accountService.getAccountByUsername(account) != null) {
            ctx.status(400);
        } else {
            ctx.json(addedAccount);
        }
    }

    private void verifyLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountToVerify = accountService.verifyLoginAttempt(account);

        if (accountToVerify == null) {
            ctx.status(401);
        } else {
            ctx.json(accountToVerify);
        }
    }

    private void getAllMessages(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageById(Context ctx) {
        if (messageService.getMessageById(ctx.pathParam("message_id")) == null) {
            ctx.result();
        } else {
            ctx.json(messageService.getMessageById(ctx.pathParam("message_id")));
        }
    }

    private void deleteMessageById(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);

        if (deletedMessage == null) {
            ctx.status(200);
        } else {
            ctx.json(deletedMessage);
        }
    }


}