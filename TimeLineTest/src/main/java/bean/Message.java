package bean;

public class Message {

    private String userName;
    private String image;
    private String information;
    private String time;

    public Message(String information,String image,String time,String userName){
        this.userName = userName;
        this.information = information;
        this.image = image;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public String getImage() {
        return image;
    }

    public String getInformation() {
        return information;
    }

    public String getTime() {
        return time;
    }
}
