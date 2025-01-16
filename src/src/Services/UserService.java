package Services;

import Exceptions.NotFoundException;
import Exceptions.WriteFileException;
import Models.Reader;
import Models.User;
import java.util.List;
import java.util.NoSuchElementException;

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

    public Reader searchUser(String cardNo) throws NotFoundException {
        while (cardNo.length() < 6) {
            cardNo = "0" + cardNo;
        }
        String finalCardNo = cardNo;
        Reader reader;
        try{
            reader = readers.stream().filter(el -> el.getCardNo().equals(finalCardNo)).findFirst().get();
            return reader;
        }catch(NoSuchElementException | NullPointerException e){
            throw new NotFoundException("User", finalCardNo);
        }
    }

    public String getNewCardNo(){
        int nextNumber = readers.size() + 1;
        return String.format("%06d", nextNumber);
    }

    public void registerUser(String name) throws WriteFileException {
        String CardNo = getNewCardNo();
        Reader reader = new Reader(CardNo, name);
        readers.add(reader);
        fileManager.saveUser(reader);
    }
}
