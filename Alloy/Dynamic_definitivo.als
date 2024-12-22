abstract sig User{}

sig Student extends User{
    var status : one StudentStatus,
}

var abstract sig StudentStatus{}
var sig NonInterested extends StudentStatus {} 
var sig Recommended extends StudentStatus {} 
var sig Applied extends StudentStatus {} 
var sig Allowed extends StudentStatus {} 
var sig RejBefForm extends StudentStatus {} 
var sig Selected extends StudentStatus {} 
var sig RejAfterForm extends StudentStatus {} 

sig Company extends User{
    var Offer: set Internship
}

sig Internship{
    var ProposedStudents: set Student,
    var InternshipStatus: one ISStatus,
    OwnedBy: one Company,
    var InterviewToCompile: one InterviewForm,
    var MiddleFeedToCompile: one MiddleFeedForm,
    var EndFeedToCompile: one EndFeedForm,
    var ReviewToCompile: one ReviewForm
}

var abstract sig ISStatus{}
var sig Registration extends ISStatus {} 
var sig FormCompiling extends ISStatus {} 
var sig SelectionPhase extends ISStatus {} 
var sig ISStarted extends ISStatus {} 
var sig ISMiddle extends ISStatus {} 
var sig ISFinished extends ISStatus {} 

sig InterviewForm{
    var InterviewCompiledBy: set Student,
    var ICBY_Company: set Company
}

sig MiddleFeedForm{
    var MiddleFeedCompiledBy: set Student,
    var MFCBY_Company: set Company
}

sig EndFeedForm{
    var EndFeedCompiledBy: set Student,
    var EFCBY_Company: set Company
}

sig ReviewForm{
    var ReviewCompiledBy: set Student,
    var RCBY_Company: set Company
}


//An internship in offered and owned by only one company
fact OwnershipConsistency {
    always all c: Company, i: Internship |
        i in c.Offer <=> i.OwnedBy = c
}

//Every internship phase has only one form to compile
fact VariousFormsUnicity{
    always all iF: InterviewForm | one iF.~InterviewToCompile
    always all mF: MiddleFeedForm | one mF.~MiddleFeedToCompile
    always all eF: EndFeedForm | one eF.~EndFeedToCompile
    always all rF: ReviewForm | one rF.~ReviewToCompile
}

//When a student apply or is recommended is proposed to the internship from the system
fact proposed{
    always all i: Internship, s: Student | i.InternshipStatus = Registration 
        and s.status = NonInterested
        implies s !in i.ProposedStudents 
    always all i: Internship, s: Student | i.InternshipStatus != Registration 
        and s.status != NonInterested
        implies s in i.ProposedStudents 
}

//Every Student that has been at least allowed to compilation of the interview form,
//have compiled it
fact Interview_Compiled{
    always all s: Student, i: Internship | 
        (s.status = Allowed or s.status = Selected or s.status = RejAfterForm)
        and s in i.ProposedStudents 
        implies s in i.InterviewToCompile.InterviewCompiledBy
    always all s: Student, i: Internship |
        (s.status != Allowed and s.status != Selected and s.status != RejAfterForm) 
        implies s !in i.InterviewToCompile.InterviewCompiledBy
}

//Every Student that is in an internship, from more than half its duration,
//have compiled the first feedback
fact Half_Work_Done{
    always all s: Student, i: Internship |
    (i.InternshipStatus = ISMiddle or i.InternshipStatus = ISFinished) and 
    s.status = Selected and s in i.ProposedStudents 
        implies (s in i.MiddleFeedToCompile.MiddleFeedCompiledBy and 
                i.OwnedBy in i.MiddleFeedToCompile.MFCBY_Company)
    always all s: Student, i: Internship | 
    (i.InternshipStatus != ISMiddle and i.InternshipStatus != ISFinished) 
       implies (s !in i.MiddleFeedToCompile.MiddleFeedCompiledBy and 
            i.OwnedBy !in i.MiddleFeedToCompile.MFCBY_Company)
}

//This fact states that students who have finished an internship
//compiled all the forms
fact students_who_have_finished{
    always all s: Student, i: Internship | i.InternshipStatus = ISFinished and
        s.status = Selected and s in i.ProposedStudents 
    implies (s in i.EndFeedToCompile.EndFeedCompiledBy and
        s in i.ReviewToCompile.ReviewCompiledBy and 
        i.OwnedBy in i.EndFeedToCompile.EFCBY_Company and
        i.OwnedBy in i.ReviewToCompile.RCBY_Company)
    always all s: Student, i: Internship | i.InternshipStatus != ISFinished 
    implies (s !in i.EndFeedToCompile.EndFeedCompiledBy and
        s !in i.ReviewToCompile.ReviewCompiledBy and 
        i.OwnedBy !in i.EndFeedToCompile.EFCBY_Company and
        i.OwnedBy !in i.ReviewToCompile.RCBY_Company)
}

//When a student is in notinterested state and do not
//apply or get recommend to an internship
fact not_in_time_registration{
    always all i: Internship, s: Student | 
            i.InternshipStatus != Registration and s.status = NonInterested 
    implies always s.status = NonInterested
}
fact init_state{
    all i: Internship | i.InternshipStatus = Registration
    all s: Student | s.status = NonInterested
}

//Internship states evolution
fact INT{
    always all i: Internship | i.InternshipStatus = Registration 
        implies after i.InternshipStatus = FormCompiling 
    always all i: Internship | i.InternshipStatus = FormCompiling 
        implies after i.InternshipStatus = SelectionPhase 
    always all i: Internship | i.InternshipStatus = SelectionPhase 
        implies after i.InternshipStatus = ISStarted 
    always all i: Internship | i.InternshipStatus = ISStarted 
        implies after i.InternshipStatus = ISMiddle 
    always all i: Internship | i.InternshipStatus = ISMiddle 
        implies after i.InternshipStatus = ISFinished 
    always all i: Internship | i.InternshipStatus = ISFinished 
        implies after i.InternshipStatus = ISFinished 
}

//Students states evolution
fact STU{
    always all s: Student | s.status = NonInterested 
        implies after (s.status = Applied or  s.status = Recommended
        or s.status = NonInterested)
    always all s: Student | (s.status = Applied or  s.status = Recommended) 
        implies after (s.status = Allowed or s.status = RejBefForm)
    always all s: Student | s.status = Allowed 
        implies after (s.status = Selected or s.status = RejAfterForm)
    always all s: Student | s.status = RejBefForm 
        implies after s.status = RejBefForm
    always all s: Student | s.status = RejAfterForm 
        implies after s.status = RejAfterForm
    always all s: Student | s.status = Selected 
        implies after s.status = Selected
}


pred show{
    one s, s1, s2, s3: Student |
        after s.status = Applied and after after s.status = Allowed
            and after after after s.status = Selected and
        after s1.status = Recommended and after after s1.status = Allowed
            and after after after s1.status = RejAfterForm and
        after s2.status = Recommended and after after s2.status = RejBefForm
        and always s3.status = NonInterested
    #Internship = 1
    #Student = 4
    #Company = 2
}
run show for 7
