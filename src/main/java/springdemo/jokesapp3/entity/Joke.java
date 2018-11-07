package springdemo.jokesapp3.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="jokes")
public class Joke {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonIgnore
	private int id;
	
	
	@Column(name="content")
	@NotBlank(message="sadrzaj mora biti popunjen") 
	private String content;
	
	@Column(name="likes")
	@JsonIgnore
	private int likes;
	
	@Column(name="dislikes")
	@JsonIgnore
	private int dislikes;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, 
			CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="category_id")
	@JsonIgnore
	private Category category;

	public Joke() {
	}

	public Joke(String content, int likes, int dislikes) {
		this.content = content;
		this.likes = likes;
		this.dislikes = dislikes;
	}
	
	public Joke(String content) {
		this.content = content;
		this.likes = 0;
		this.dislikes = 0;	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
//	@JsonIgnore
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	@JsonIgnore
	public int getDifference() {
		return Math.abs(likes-dislikes);
	}
	
	@Override
	public String toString() {
		return "Joke [id=" + id + ", content=" + content + ", likes=" + likes + ", dislikes=" + dislikes + "]";
	}

}
