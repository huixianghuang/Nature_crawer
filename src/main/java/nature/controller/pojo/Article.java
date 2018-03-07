package nature.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private String URL;
    private String Title;
    private int Index_id;
    private Date Recevied;
    private Date Accepted;
    private Date Published_online;
    private int Web_of_Science;
    private int CrossRef;
    private int Scopus;
    private int Altmetric;

}
