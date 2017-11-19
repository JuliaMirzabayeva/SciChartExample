package myapp.scichartexample.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Julia on 18.11.2017.
 */

public class Point implements Serializable {
    private Date date;
    private Double rate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
