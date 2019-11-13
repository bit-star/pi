import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

export interface IProcessTemplateMpPi {
  id?: number;
  name?: string;
  processCode?: string;
  processInstances?: IProcessInstanceMpPi[];
}

export class ProcessTemplateMpPi implements IProcessTemplateMpPi {
  constructor(public id?: number, public name?: string, public processCode?: string, public processInstances?: IProcessInstanceMpPi[]) {}
}
