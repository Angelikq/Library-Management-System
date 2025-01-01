package Services;

import Models.Reader;
import Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<Reader> readers;
    private FileManager fileManager;

    public UserService(List<Reader> readers, FileManager fileManager) {
        this.readers = readers;
        this.fileManager = fileManager;
    }

    public void listUsers() {
        for (User user : readers) {
            System.out.println(user.getName() + " - " + user.getRole());
        }
    }

    public List<Reader> searchUser(String cardNo) {
        while (cardNo.length() < 6) {
            cardNo = "0" + cardNo;
        }
        List<Reader> foundUser = new ArrayList<>();
        for (Reader reader : readers) {
            if (reader.getCardNo().contains(cardNo) ) {
                foundUser.add(reader);
            }
        }
        return foundUser;
    }

    public String getNewCardNo(){
        int nextNumber = readers.size() + 1;
        return String.format("%06d", nextNumber);
    }

    public void registerUser(Reader reader) {
        readers.add(reader);
        fileManager.saveUser(reader);
    }
}
