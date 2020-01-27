export class Road {
    division: string
    district: string
    upazilla: string
    postalCode: string
    location: string
    startPoint:string
    endPoint: string
    purpose: string                 
    isriverOrWaterbodynear :string
    status: string
    nextStep:string
}

export class StatusCheck {
    oid : string
    nextStep: string
    currentStepOid : string
    fromstepOid : string
    step: string
    fileName : any
    actualFilename : any
}

export class Comment {
    roadRequisitionOid : string
    comment: string
    stepOid:string
}

export class StepInstance {
    roadrequisitionoid : string
    stepoid: string
    isdone:string
    isactive:string
}

export class FileDownload{
    fileName: string
}