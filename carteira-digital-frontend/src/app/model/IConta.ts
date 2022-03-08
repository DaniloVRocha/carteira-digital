import { ICliente } from './ICliente';
import { IOperacao } from './IOperacao';

export interface IConta {

    id?: number;
	  instituicao?: string;
    saldo?: number;
    despesas?: number;
    receitas?: number;
	  cliente?: ICliente;
	  operacao?: IOperacao[];

}
