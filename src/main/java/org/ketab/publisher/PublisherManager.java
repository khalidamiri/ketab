package org.ketab.publisher;

import java.util.List;

public interface PublisherManager {

	public void addPub(Publisher publisher);
	public Publisher getPub(long pubId);
	public void delPub(long pubId);
	public void updatePub(Publisher publisher);
	public List listPubs(String sortBy, String sortType);
}
