package my.diploma.demo.objects;

public class Report {
    private String title;
    private double sum;

    public Report(){}

    public Report(String title, double sum){
        this.title = title;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
