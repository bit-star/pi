import { Moment } from 'moment';
import { IProcessInstanceEventMpPi } from 'app/shared/model/process-instance-event-mp-pi.model';
import { IFormComponentValuesMpPi } from 'app/shared/model/form-component-values-mp-pi.model';
import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { IProcessTemplateMpPi } from 'app/shared/model/process-template-mp-pi.model';

export interface IProcessInstanceMpPi {
  id?: number;
  attachedProcessInstanceIds?: string;
  bizAction?: string;
  businessId?: string;
  createTime?: Moment;
  finishTime?: Moment;
  formComponentValues?: string;
  operationRecords?: string;
  originatorDeptId?: string;
  originatorDeptName?: string;
  originatorUserid?: string;
  result?: string;
  status?: string;
  tasks?: string;
  title?: string;
  processInstanceEvents?: IProcessInstanceEventMpPi[];
  formComponentValues?: IFormComponentValuesMpPi[];
  ddMessages?: IDdMessageMpPi[];
  processTemplate?: IProcessTemplateMpPi;
}

export class ProcessInstanceMpPi implements IProcessInstanceMpPi {
  constructor(
    public id?: number,
    public attachedProcessInstanceIds?: string,
    public bizAction?: string,
    public businessId?: string,
    public createTime?: Moment,
    public finishTime?: Moment,
    public formComponentValues?: string,
    public operationRecords?: string,
    public originatorDeptId?: string,
    public originatorDeptName?: string,
    public originatorUserid?: string,
    public result?: string,
    public status?: string,
    public tasks?: string,
    public title?: string,
    public processInstanceEvents?: IProcessInstanceEventMpPi[],
    public formComponentValues?: IFormComponentValuesMpPi[],
    public ddMessages?: IDdMessageMpPi[],
    public processTemplate?: IProcessTemplateMpPi
  ) {}
}
