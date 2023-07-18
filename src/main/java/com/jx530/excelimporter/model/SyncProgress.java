package com.jx530.excelimporter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Table(name = "sync_progress")
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SyncProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sync_progress")
    private Long id;
    @Version
    @Column(nullable = false)
    private Long version;
    @Column(nullable = false, columnDefinition = "varchar(64)")
    @Enumerated(EnumType.STRING)
    private SyncType type;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String progress;
    private boolean finished;
    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created;
    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime modified;

    public SyncProgress(SyncType type, String progress, boolean finished) {
        this.type = type;
        this.progress = progress;
        this.finished = finished;
        this.version = 1L;
    }

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Progress {
        LocalDateTime current;
        LocalDateTime latest;
    }
}
