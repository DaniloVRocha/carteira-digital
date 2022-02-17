import { IConta } from "./IConta";

export interface IOperacao {

    id: number;
	dataHora: Date;
    instituicao:string;
    vencimento: Date;
    categoria: string;
    tpOperacao: CharacterData;
	valor: number;
	estadoPagamento: string;
    conta: IConta;
    observacao: string

}