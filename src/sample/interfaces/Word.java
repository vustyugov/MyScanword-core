package sample.interfaces;

/**
 * Created by VAUst on 31.12.2016.
 */
public interface Word {
    public void setDescription (String description);
    public void setCategories (List<Category> categories);
    public void setChangeHistory (Date changeHistory, String value,
                                  String description, List<Category> categories);
    public void addCategory (Category category);
    public void removeCategory (Category category);
    public List<Category> getCategories ();
    public String getValue ();
    public String getDescription ();
}
