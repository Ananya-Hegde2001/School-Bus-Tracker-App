package com.example.bustracker1;

public class getdata {
    String parent,student,pno,roll,busno,email;

    public getdata(){

    }


    public getdata(String pname, String sname, String phoneno, String rollno, String busno, String email) {
        this.parent=pname;
        this.student=sname;
        this.pno=phoneno;
        this.roll=rollno;
        this.busno=busno;
        this.email=email;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}
}
