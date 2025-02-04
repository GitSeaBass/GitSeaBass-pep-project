package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(String message_id) {
        int message_idToInt = Integer.parseInt(message_id);
        return messageDAO.getMessageById(message_idToInt);
    }

    public Message deleteMessageById(int message_id) {
        if (messageDAO.getMessageById(message_id) == null) {
            return null;
        }

        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, Message message) {
        if (messageDAO.getMessageById(message_id) == null || message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }
        messageDAO.updateMessageById(message_id, message);
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesByAccountId(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }

    public Message addMessage(Message message) {
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255) {
            return null;
        }

        return messageDAO.insertMessage(message);
    }
}
