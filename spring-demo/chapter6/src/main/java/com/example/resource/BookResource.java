package com.example.resource;

import java.time.LocalDate;

// (record に限らず) Serializable は継承してなくても動く？
// フィールド名を JSON から変えたい場合は @JsonProperty
public record BookResource(Long id, String title, LocalDate publishedDate) {}
