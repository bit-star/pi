import { IProcessInstanceMpPi } from 'app/shared/model/process-instance-mp-pi.model';
import { DdMessageType } from 'app/shared/model/enumerations/dd-message-type.model';
import { MessageStatus } from 'app/shared/model/enumerations/message-status.model';

export interface IDdMessageMpPi {
  id?: number;
  receivingDepartment?: string;
  receivingUser?: string;
  title?: string;
  json?: string;
  type?: DdMessageType;
  status?: MessageStatus;
  processInstance?: IProcessInstanceMpPi;
}

export class DdMessageMpPi implements IDdMessageMpPi {
  constructor(
    public id?: number,
    public receivingDepartment?: string,
    public receivingUser?: string,
    public title?: string,
    public json?: string,
    public type?: DdMessageType,
    public status?: MessageStatus,
    public processInstance?: IProcessInstanceMpPi
  ) {}
}
