import { IConta } from "./IConta";

export interface IOperacao {

    id: number;
	dataHora: string;
    vencimento: string;
    categoria: string;
    tpOperacao: CharacterData;
	valor: number;
	estadoPagamento: string;
    conta: IConta;
    observacao: string

}