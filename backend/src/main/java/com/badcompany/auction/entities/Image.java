package com.badcompany.auction.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "status")
    private EStatus status;

    @Column(name = "type")
    private EImageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    @JsonIgnore
    private Lot lot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EImageType getType() {
        return type;
    }

    public void setType(EImageType type) {
        this.type = type;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Image() {
        super();
    }

    public Image(Long id, String fileName, EImageType type) {
        super();
        this.id = id;
        this.fileName = fileName;
        this.status = EStatus.STATUS_USED;
        this.type = type;
    }

    public Image(String fileName, EImageType type) {
        super();
        this.fileName = fileName;
        this.status = EStatus.STATUS_USED;
        this.type = type;
    }

    public Image(String fileName, Lot lot) {
        super();
        this.fileName = fileName;
        this.status = EStatus.STATUS_USED;
        this.type = EImageType.TYPE_LOT;
        this.lot = lot;
    }
}
