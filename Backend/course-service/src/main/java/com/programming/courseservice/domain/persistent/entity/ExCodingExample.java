//package com.programming.courseservice.domain.persistent.entity;
//
//import com.main.progamming.common.util.SystemUtil;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Table(
//        name = "ex_coding_example"
//)
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ExCodingExample {
//    @Id
//    private String id;
//
//    @Column(length = 128)
//    private String input;
//
//    @Column(length = 128)
//    private String output;
//
//    private String description;
//
//    @Lob
//    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
//    private byte[] image;
//
//    private Long created;
//
//    @ManyToOne(targetEntity = ExCoding.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "ex_coding_id", foreignKey = @ForeignKey(name = "fk_ex_coding_example_ex_coding"))
//    private ExCoding exCoding;
//
//    @PrePersist
//    protected void ensureId() {
//        this.setId(UUID.randomUUID().toString());
//        this.setCreated(System.currentTimeMillis());
//    }
//
//    @PreUpdate
//    protected void setUpdated() {
//        this.setCreated(System.currentTimeMillis());
//    }
//}
