import { IConta } from "./IConta";

export interface IOperacao {

    id: number;
	  dataHora: Date;
    instituicao:string;
    vencimento: string;
    categoria: number;
    tpOperacao: string;
	  valor: number;
	  estadoPagamento: string;
    conta: IConta;
    nome: string

}
