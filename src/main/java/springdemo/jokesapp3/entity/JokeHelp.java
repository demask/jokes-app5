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


@Component
public class JokeHelp {

	private int id;

	@NotBlank(message = "sadržaj mora sadržavati tekst")
	private String content;

	private int likes;

	private int dislikes;

	@NotNull(message = "kategorija mora biti popunjena")
	private String category;

	public JokeHelp() {
		
		
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getDifference() {
		return Math.abs(likes - dislikes);
	}
	
	public void setClear() {
		this.likes = 0;
		this.dislikes = 0;
		this.content = "";
		this.category = "";
	}

	@Override
	public String toString() {
		return "Joke [id=" + id + ", content=" + content + ", likes=" + likes + ", dislikes=" + dislikes + "]";
	}

}
