package org.geekhub.example.entity;

@SuppressWarnings("java:S6218")
public record FileContent(
    FileInfo fileInfo,
    byte[] content
) {
}
