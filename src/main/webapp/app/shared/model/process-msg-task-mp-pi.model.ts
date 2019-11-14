import { Moment } from 'moment';
import { IDdMessageMpPi } from 'app/shared/model/dd-message-mp-pi.model';
import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { MessageStatus } from 'app/shared/model/enumerations/message-status.model';

export interface IProcessMsgTaskMpPi {
  id?: number;
  receivingDepartment?: string;
  receivingUser?: string;
  title?: string;
  json?: string;
  executeTime?: Moment;
  status?: MessageStatus;
  ddMessages?: IDdMessageMpPi[];
  processInstance?: IProcessInstanceMpPi;
}

export class ProcessMsgTaskMpPi implements IProcessMsgTaskMpPi {
  constructor(
    public id?: number,
    public receivingDepartment?: string,
    public receivingUser?: string,
    public title?: string,
    public json?: string,
    public executeTime?: Moment,
    public status?: MessageStatus,
    public ddMessages?: IDdMessageMpPi[],
    public processInstance?: IProcessInstanceMpPi
  ) {}
}
