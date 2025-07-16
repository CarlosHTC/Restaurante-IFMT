package ifmt.cba.restaurante.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO {
    
    private int codigo;
    private String nome;
    private float custoUnidade;
    private int valorEnergetico;
    private int estoque;
    private int estoqueMinimo;
    private GrupoAlimentarDTO grupoAlimentar;

    public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getCustoUnidade() {
		return custoUnidade;
	}

	public void setCustoUnidade(float custoUnidade) {
		this.custoUnidade = custoUnidade;
	}

	public int getValorEnergetico() {
		return valorEnergetico;
	}

	public void setValorEnergetico(int valorEnergetico) {
		this.valorEnergetico = valorEnergetico;
	}

	
	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public GrupoAlimentarDTO getGrupoAlimentar() {
		return grupoAlimentar;
	}

	public void setGrupoAlimentar(GrupoAlimentarDTO grupoAlimentar) {
		this.grupoAlimentar = grupoAlimentar;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
