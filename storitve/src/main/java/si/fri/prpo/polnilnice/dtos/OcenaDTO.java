package si.fri.prpo.polnilnice.dtos;

public class OcenaDTO {

        private UporabnikDTO uporabnikDTO;
        private Integer ocena;
        private String komentar;

    public UporabnikDTO getUporabnikDTO() {
        return uporabnikDTO;
    }

    public void setUporabnikDTO(UporabnikDTO uporabnikDTO) {
        this.uporabnikDTO = uporabnikDTO;
    }

    public Integer getOcena() {
            return ocena;
        }

        public void setOcena(Integer ocena) {
            this.ocena = ocena;
        }

        public String getKomentar() {
            return komentar;
        }

        public void setKomentar(String komentar) {
            this.komentar = komentar;
        }
}
