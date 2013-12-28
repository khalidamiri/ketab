package org.ketab.category;

import java.util.List;


public interface CategoryManager {

	public void addCat(Category category);
	public Category getCat(long catId);
	public void delCat(long catId);
	public void updateCat(Category category);
	public List listCats(String sortBy, String sortType);
}
