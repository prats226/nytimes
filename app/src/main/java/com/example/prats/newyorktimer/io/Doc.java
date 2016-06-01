package com.example.prats.newyorktimer.io;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prats on 1/6/16.
 */
public class Doc {
    public String web_url;
    public String snippet;
    public String lead_paragraph;
    @SerializedName("abstract")
    public String abstractString;
    public String source;
    public Headline headline;
    public String pub_date;
    public String document_type;
    public String news_desk;
    public String section_name;
    public String type_of_material;
    public String _id;
    public Multimedia[] multimedia;
}
