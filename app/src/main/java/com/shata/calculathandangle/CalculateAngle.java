package com.shata.calculathandangle;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculateAngle {

    public ArrayList<HashMap> listFingerPoint;
    public ArrayList<Double> resultAngleCos;
    public ArrayList<Double> resultAngleTan;

    public CalculateAngle() {
        listFingerPoint = new ArrayList<>();
        resultAngleCos = new ArrayList<>();
        resultAngleTan = new ArrayList<>();
    }

    public CalculateAngle(ArrayList<HashMap> listFingerPoint) {
        this.listFingerPoint = listFingerPoint;
        this.resultAngleCos = new ArrayList<>();
        this.resultAngleTan = new ArrayList<>();

        this.resultAngleCos = AngleResultCos();
        this.resultAngleTan = AngleResultTan();
    }

    public ArrayList<Double> AngleResultCos() {

        for (int i = 2; i < listFingerPoint.size(); i++) {
            float AX = (float) listFingerPoint.get(0).get("X");
            float AY = (float) listFingerPoint.get(0).get("Y");

            float BX = (float) listFingerPoint.get(i - 1).get("X");
            float BY = (float) listFingerPoint.get(i - 1).get("Y");

            float CX = (float) listFingerPoint.get(i).get("X");
            float CY = (float) listFingerPoint.get(i).get("Y");

            double fingerAX = BX - AX;
            double fingerAY = BY - AY;

            double fingerBX = CX - AX;
            double fingerBY = CY - AY;

            double FA_FB = fingerAX * fingerBX + fingerAY * fingerBY;
            double lfA = Math.sqrt(Math.pow(fingerAX, 2) + Math.pow(fingerAY, 2));
            double lfB = Math.sqrt(Math.pow(fingerBX, 2) + Math.pow(fingerBY, 2));

            double angle_R = FA_FB / (lfA * lfB);
            double theta = Math.toDegrees(Math.acos(angle_R));

            resultAngleCos.add(theta);
        }
        return resultAngleCos;
    }

    public ArrayList<Double> AngleResultTan() {

        for (int i = 2; i < listFingerPoint.size(); i++) {
            float AX = (float) listFingerPoint.get(0).get("X");
            float AY = (float) listFingerPoint.get(0).get("Y");

            float BX = (float) listFingerPoint.get(i - 1).get("X");
            float BY = (float) listFingerPoint.get(i - 1).get("Y");

            float CX = (float) listFingerPoint.get(i).get("X");
            float CY = (float) listFingerPoint.get(i).get("Y");

            double m1 = (BX - AX) / (BY - AY);
            double m2 = (CX - AX) / (CY - AY);

            double tan = Math.abs((m1 - m2) / (1 + (m1 * m2)));

            double theta = Math.toDegrees(Math.atan(tan));

            resultAngleTan.add(theta);
        }
        return resultAngleTan;
    }

    public ArrayList<HashMap> getListFingerPoint() {
        return listFingerPoint;
    }

    public void setListFingerPoint(ArrayList<HashMap> listFingerPoint) {
        this.listFingerPoint = listFingerPoint;
    }

    public ArrayList<Double> getResultAngleCos() {
        return resultAngleCos;
    }

    public ArrayList<Double> getResultAngleTan() {
        return resultAngleTan;
    }
}
