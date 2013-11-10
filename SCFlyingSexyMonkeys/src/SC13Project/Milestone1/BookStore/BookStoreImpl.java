package SC13Project.Milestone1.BookStore;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.example.bookcategorydb.BookCategoryWS;
import org.example.bookcategorydb.BookInfo;
import org.example.bookcategorydb.CategoryInfo;
import org.example.bookcategorydb.ObjectFactory;

//Please do not change the name of the package or this interface
//Please add here your implementation
public class BookStoreImpl implements BookCategoryWS {

	public List<BookInfo> getNodes() {
		
		ClassLoader cl = ObjectFactory.class.getClassLoader();
		String packageName = CategoryInfo.class.getPackage().getName();

		List<BookInfo> bi = new ArrayList<BookInfo>();

		try {
			JAXBContext jc = JAXBContext.newInstance(packageName, cl);
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement<CategoryInfo> root = (JAXBElement<CategoryInfo>)u.unmarshal(new FileInputStream("src/SC13Project/Milestone1/BookStore/BookCategoryDB.xml"));
			CategoryInfo ci = root.getValue();
			bi = ci.getBook();

			if(bi.size() == 0) {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bi;
	}

	@Override
	public List<String> getAllBooKNames() {
		// TODO Auto-generated method stub

		List<String> bookNames = new ArrayList<String>();

		try {
				List<BookInfo> bi = getNodes();
	
				for(BookInfo b : bi) {
					bookNames.add(b.getTitle());
				}
	
	
			} catch(Exception e) {
				e.printStackTrace();
			}
			return bookNames;
	}

	@Override
	public List<String> getAllBookISBN10() {
		// TODO Auto-generated method stub

		List<String> bookISBN10 = new ArrayList<String>();

		try {
			List<BookInfo> bi = getNodes();

			for(BookInfo b : bi) {
				bookISBN10.add(b.getISBN10());
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		return bookISBN10;
	}

	@Override
	public List<String> getAllBookISBN13() {
		// TODO Auto-generated method stub

		List<String> bookISBN13 = new ArrayList<String>();

		try {
			List<BookInfo> bi = getNodes();

			for(BookInfo b : bi) {
				bookISBN13.add(b.getISBN13());
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		return bookISBN13;
	}

	@Override
	public List<BookInfo> getBooksByTitle(String title) {
		// TODO Auto-generated method stub

		List<BookInfo> books = new ArrayList<BookInfo>();

		try {
			List<BookInfo> bi = getNodes();
			
			for(BookInfo b : bi) {
				if(b.getTitle().equalsIgnoreCase(title)) {
					books.add(b);
				}
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public List<BookInfo> getBooksByAuthor(String author) {
		// TODO Auto-generated method stub

		List<BookInfo> books = new ArrayList<BookInfo>();

		try {

			List<BookInfo> bi = getNodes();
			
			for(BookInfo b : bi) {
				if(b.getAuthors().getAuthor().equals(author)) {
					books.add(b);
				}
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public BookInfo getBookInfobyISBN10(String isbn10) {
		// TODO Auto-generated method stub


		try {
			List<BookInfo> bi = getNodes();

			for(BookInfo b : bi) {
				if(b.getISBN10().equalsIgnoreCase(isbn10)) {
					return b;
				}
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookInfo getBookInfobyISBN13(String isbn13) {
		// TODO Auto-generated method stub

		try {
			List<BookInfo> bi = getNodes();

			for(BookInfo b : bi) {
				if(b.getISBN13().equalsIgnoreCase(isbn13)) {
					return b;
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
