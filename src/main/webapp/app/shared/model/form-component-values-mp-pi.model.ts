import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

export interface IFormComponentValuesMpPi {
  id?: number;
  componentType?: string;
  value?: string;
  name?: string;
  extValue?: string;
  processInstance?: IProcessInstanceMpPi;
}

export class FormComponentValuesMpPi implements IFormComponentValuesMpPi {
  constructor(
    public id?: number,
    public componentType?: string,
    public value?: string,
    public name?: string,
    public extValue?: string,
    public processInstance?: IProcessInstanceMpPi
  ) {}
}
