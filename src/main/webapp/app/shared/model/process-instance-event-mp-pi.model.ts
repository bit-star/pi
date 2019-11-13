import { Moment } from 'moment';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';

export interface IProcessInstanceEventMpPi {
  id?: number;
  finishTime?: Moment;
  corpId?: string;
  eventType?: string;
  businessId?: string;
  title?: string;
  type?: string;
  url?: string;
  result?: string;
  createTime?: string;
  processCode?: string;
  bizCategoryId?: string;
  staffId?: string;
  processInstance?: IProcessInstanceMpPi;
}

export class ProcessInstanceEventMpPi implements IProcessInstanceEventMpPi {
  constructor(
    public id?: number,
    public finishTime?: Moment,
    public corpId?: string,
    public eventType?: string,
    public businessId?: string,
    public title?: string,
    public type?: string,
    public url?: string,
    public result?: string,
    public createTime?: string,
    public processCode?: string,
    public bizCategoryId?: string,
    public staffId?: string,
    public processInstance?: IProcessInstanceMpPi
  ) {}
}
