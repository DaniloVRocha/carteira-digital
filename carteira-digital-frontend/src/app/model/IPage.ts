import { IOperacao } from 'src/app/model/IOperacao';

export interface IPage {
  content: Array<IOperacao>;
  totalPages: number;
  totalElements: number;
  last: boolean;
  number: number;
  sort?: any;
  first: boolean;
  numberOfElements: number;
  size: number;
  empty: boolean;
};
