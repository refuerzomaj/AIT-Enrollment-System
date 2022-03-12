
package enrollmentsystem;

public class user {
    private String employeeId;
    private String username;
    private String lastname,firstname,middlename;
    private String gender;
    private int age;
    private String status;
    private String email;
    private String contact;
    private String address;
    private String position;
    private String usertType;
    public user(
            String employeeId,
            String username,
            String lastname,
            String firstname,
            String middlename,
            String gender,
            int age,
            String status,
            String email,
            String contact,
            String address,
            String position,
            String userType){
        this.employeeId=employeeId;
        this.username=username;
        this.lastname=lastname;
        this.firstname=firstname;
        this.middlename=middlename;
        this.gender=gender;
        this.age=age;
        this.status=status;
        this.email=email;
        this.contact=contact;
        this.address=address;
        this.position=position;
        this.usertType=userType;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public String getUsername(){
        return username;
    }
    public String getLastname(){
        return lastname;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getMiddlename(){
        return middlename;
    }
    public String getGender(){
        return gender;
    }
    public int getAge(){
        return age;
    }
    public String getStatus(){
        return status;
    }
    public String getEmail(){
        return email;
    }
    public String getContact(){
        return contact;
    }
    public String getAddress(){
        return address;
    }
    public String getPosition(){
        return position;
    }
    public String getUserType(){
        return usertType;
    }
}
