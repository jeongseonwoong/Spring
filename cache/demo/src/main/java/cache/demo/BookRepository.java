package cache.demo;



public interface BookRepository {

    String getTitleByIsbn(String isbn);
    String getTitleByIsbnUsingCache(String isbn);
}
