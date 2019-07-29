package com.mark.sleevecoach.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by user1 on 3/27/2017.
 */
public class EatFeel extends DataSupport {

    public String eating;
    public String feeling;
    public long date;
}
