package edu.umsl;

public class PayrollResults {

    private float gross = 0.0f;
    private float tax = 0.0f;
    private float net = 0.0f;
    private float netPercent = 0.0f;

    public PayrollResults(float gross, float tax, float net, float netPercent) {
        this.gross = gross;
        this.tax = tax;
        this.net = net;
        this.netPercent = netPercent;
    }

    public float getGross() {
        return gross;
    }

    public float getTax() {
        return tax;
    }

    public float getNet() {
        return net;
    }

    public float getNetPercent() {
        return netPercent;
    }
}
