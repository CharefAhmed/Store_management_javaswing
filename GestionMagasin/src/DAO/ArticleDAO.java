package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Métier.Article;

public class ArticleDAO {
    Connection connection=SingletonConnection.getInstance();
    public static int qtedec;
    public void insertArticle(Article article) throws SQLException {
        String sql = "INSERT INTO articles (designation, categorie, quantite,prixUnitaire,etatArt)  VALUES ( ?, ?, ?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, article.getDesignation());
            statement.setString(2, article.getCategorie());
            statement.setInt(3, article.getQuantite());
            statement.setDouble(4, article.getPrixUnitaire());
            statement.setString(5, article.getEtatArt());
            statement.executeUpdate(); }
    }
    public void deleteArticle(Article article)throws SQLException{
    	String sql ="delete from articles where idArticle = ? ";
    	try(PreparedStatement statement = connection.prepareStatement(sql)){
    		statement.setInt(1,article.getIdArticle());
            statement.executeUpdate();}
    }
    public void updateArticle(Article article) throws SQLException {
        String sql = "UPDATE articles SET designation = ?, categorie = ?, quantite = ?, prixUnitaire = ?, etatArt=? WHERE idArticle = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, article.getDesignation());
            statement.setString(2, article.getCategorie());
            statement.setInt(3, article.getQuantite());
            statement.setDouble(4, article.getPrixUnitaire());
            statement.setString(5, article.getEtatArt());
            statement.setInt(6, article.getIdArticle());
            statement.executeUpdate(); }
    }
    public void updateArticleQte(Article article) throws SQLException {
        String sql = "UPDATE articles SET  quantite = ? WHERE idArticle = ? ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getQuantite());
            statement.setInt(2, article.getIdArticle());
            statement.executeUpdate(); }
    }
    public int getArticleQteByID(int idArticle) throws SQLException{
    	
    	int quantite=0;
    	String sql="select quantite From articles where idArticle=?";
    	try (PreparedStatement statement=connection.prepareStatement(sql)){
    		statement.setInt(1, idArticle);
    		try(ResultSet resultset=statement.executeQuery()){    			
    			while(resultset.next()) {
    				
    				quantite=resultset.getInt("quantite");
    				

    			}
    		}
    	}
    	return quantite;
    }

    public List<Article> getAllArticles() throws SQLException{
    	List<Article> articles=new ArrayList<Article>();
    	int idArticle;
    	String designation;
    	String categorie;
    	int quantite;
    	double prixUnitaire;
    	String etatArt;
    	String sql="select * From articles";
    	try (PreparedStatement statement=connection.prepareStatement(sql)){
    		try(ResultSet resultset=statement.executeQuery()){    			
    			while(resultset.next()) {
    				idArticle=resultset.getInt("idArticle");
    				designation=resultset.getString("designation");
    				categorie=resultset.getString("categorie");
    				quantite=resultset.getInt("quantite");
    				prixUnitaire=resultset.getDouble("prixUnitaire");
    				etatArt=resultset.getString("etatArt");
    				Article article=new Article(idArticle,designation,categorie,quantite,prixUnitaire,etatArt);
    				articles.add(article);}

    		}
    	}
    	return articles;
    }

    public List<Article> getAllArticlesEnStock() throws SQLException{
    	List<Article> articles=new ArrayList<Article>();
    	int idArticle;
    	String designation;
    	String categorie;
    	int quantite;
    	double prixUnitaire;
    	String etatArt;
    	String sql="select * From articles where etatArt='en stock'";
    	try (PreparedStatement statement=connection.prepareStatement(sql)){
    		try(ResultSet resultset=statement.executeQuery()){    			
    			while(resultset.next()) {
    				idArticle=resultset.getInt("idArticle");
    				designation=resultset.getString("designation");
    				categorie=resultset.getString("categorie");
    				quantite=resultset.getInt("quantite");
    				prixUnitaire=resultset.getDouble("prixUnitaire");
    				etatArt=resultset.getString("etatArt");
    				Article article=new Article(idArticle,designation,categorie,quantite,prixUnitaire,etatArt);
    				articles.add(article);
    			}
    		}
    	}
    	return articles;
    }
    public Article getArticleByDesignation(String designation) throws SQLException {
        String sql = "SELECT * FROM articles WHERE designation = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, designation);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idArticle = resultSet.getInt("idArticle");
                    String articleDesignation = resultSet.getString("designation");
                    String categorie = resultSet.getString("categorie");
                    int quantite = resultSet.getInt("quantite");
                    double prixUnitaire = resultSet.getDouble("prixUnitaire");
                    String etatArt=resultSet.getString("etatArt");
                    return new Article(idArticle, articleDesignation, categorie, quantite, prixUnitaire,etatArt);
                }
            }
        }
        return null;
    }
    public List<Article> getArticlesByDesignation(String designation) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE designation LIKE ? AND etatArt='En Stock' ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setString(1, "%" + designation + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idArticle =resultSet.getInt("idArticle");
                String articleDesignation = resultSet.getString("designation");
                String categorie = resultSet.getString("categorie");
                int quantite = resultSet.getInt("quantite");
                double prixUnitaire = resultSet.getDouble("prixUnitaire");
                String etatArt=resultSet.getString("etatArt");
                Article article =new Article(idArticle, articleDesignation, categorie, quantite, prixUnitaire,etatArt);
                articles.add(article);
            }
        }
        return articles;
    }
    public List<Article> getArticlesByCategory(String categorie) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE categorie LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + categorie + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idArticle = resultSet.getInt("idArticle");
                    String designation = resultSet.getString("designation");
                    String category = resultSet.getString("categorie");
                    int quantite = resultSet.getInt("quantite");
                    double prixUnitaire = resultSet.getDouble("prixUnitaire");
                    String etatArt = resultSet.getString("etatArt");
                    Article article = new Article(idArticle, designation, category, quantite, prixUnitaire, etatArt);
                    articles.add(article);
                }
            }
        }
        return articles;
    }
    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT * FROM articles WHERE idArticle = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idArticle = resultSet.getInt("idArticle");
                    String designation = resultSet.getString("designation");
                    String categorie = resultSet.getString("categorie");
                    int quantite = resultSet.getInt("quantite");
                    double prixUnitaire = resultSet.getDouble("prixUnitaire");
                    String etatArt=resultSet.getString("etatArt");
                    return new Article(idArticle, designation, categorie, quantite, prixUnitaire,etatArt);
                }
            }
        }
        return null;}
    public List<Article> getArticlesByEtat(String etatArticle) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE etatArt = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, etatArticle);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idArticle = resultSet.getInt("idArticle");
                    String designation = resultSet.getString("designation");
                    String categorie = resultSet.getString("categorie");
                    int quantite = resultSet.getInt("quantite");
                    double prixUnitaire = resultSet.getDouble("prixUnitaire");
                    String etatArt = resultSet.getString("etatArt");
                    Article article = new Article(idArticle, designation, categorie, quantite, prixUnitaire, etatArt);
                    articles.add(article);
                }
            }
        }
        return articles;
    }
}
