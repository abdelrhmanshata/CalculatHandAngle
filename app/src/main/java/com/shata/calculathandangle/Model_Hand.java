package com.shata.calculathandangle;

import java.io.Serializable;

public class Model_Hand implements Serializable {

    int ID;
    String imageName;
    String angle1 ,angle2,angle3,angle4;

    public Model_Hand() {
    }

    public Model_Hand(int ID, String imageName, String angle1, String angle2, String angle3, String angle4) {
        this.ID = ID;
        this.imageName = imageName;
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.angle3 = angle3;
        this.angle4 = angle4;
    }

    public Model_Hand(String imageName, String angle1, String angle2, String angle3, String angle4) {
        this.imageName = imageName;
        this.angle1 = angle1;
        this.angle2 = angle2;
        this.angle3 = angle3;
        this.angle4 = angle4;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAngle1() {
        return angle1;
    }

    public void setAngle1(String angle1) {
        this.angle1 = angle1;
    }

    public String getAngle2() {
        return angle2;
    }

    public void setAngle2(String angle2) {
        this.angle2 = angle2;
    }

    public String getAngle3() {
        return angle3;
    }

    public void setAngle3(String angle3) {
        this.angle3 = angle3;
    }

    public String getAngle4() {
        return angle4;
    }

    public void setAngle4(String angle4) {
        this.angle4 = angle4;
    }
}
