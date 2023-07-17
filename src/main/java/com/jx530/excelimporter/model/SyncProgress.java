package com.jx530.excelimporter.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Table(name = "sync_progress")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SyncProgress {

    @Id
    private Long id;
    @Version
    private Long version;
    private Long contextId;
    private String progress;
    private boolean finished;
    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime modified;

    public void setProgress(Progress progress) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String curr = formatter.format(progress.getCurrent());
        String latest = formatter.format(progress.getLatest());
        this.progress = curr + "/" + latest;
    }

    public Progress getProgress() {
        String[] split = this.progress.split("/");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Progress() {
            public LocalDateTime getCurrent() {
                return LocalDateTime.parse(split[0], formatter);
            }

            public LocalDateTime getLatest() {
                return LocalDateTime.parse(split[1], formatter);
            }
        };
    }

    public interface Progress {
        LocalDateTime getCurrent();

        LocalDateTime getLatest();
    }
}
