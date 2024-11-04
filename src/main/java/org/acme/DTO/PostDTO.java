package org.acme.DTO;

import java.time.LocalDateTime;

public class PostDTO {
    public Long id;
    public String content;
    public Long authorId;
    public Long streamId;
    public LocalDateTime timestamp;
}
