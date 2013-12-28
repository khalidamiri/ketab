package org.ketab.review;

import java.util.List;

public interface ReviewManager {

	public void addRev(Review rev);
	public Review getRev(long revId);
	public void delRev(long revId);
	public void updateRev(Review rev);
	public List listRevs(String sortBy, String sortType);
}
