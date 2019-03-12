package hiof.gr19.seat;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String emailAdress;

    public Person(String firstName, String lastName, int age, String emailAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.emailAdress = emailAdress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
    
}
