package springdemo.jokesapp3.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
