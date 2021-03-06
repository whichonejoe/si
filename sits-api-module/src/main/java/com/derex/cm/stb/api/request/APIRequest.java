/**
 * 
 */
package com.derex.cm.stb.api.request;

/**
 * @author Nandin
 *
 */
public class APIRequest implements java.io.Serializable {

	private static final long serialVersionUID = 3697475920692262198L;
	private Object content;
	private int page = 1;
	private int limit = 10;

	public APIRequest(Object content) {
		super();
		this.content = content;
	}

	public APIRequest(Object content, int page, int limit) {
		super();
		this.content = content;
		this.page = page;
		this.limit = limit;
	}

	public Object getContent() {
		return content;
	}

	public int getPage() {
		return page;
	}

	public int getLimit() {
		return limit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APIRequest [content=\n");
		builder.append(content);
		builder.append(", page=\n");
		builder.append(page);
		builder.append(", limit=\n");
		builder.append(limit);
		builder.append("]");
		return builder.toString();
	}

}
