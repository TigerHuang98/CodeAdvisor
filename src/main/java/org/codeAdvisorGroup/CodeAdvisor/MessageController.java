package org.codeAdvisorGroup.CodeAdvisor;

import org.codeAdvisorGroup.CodeAdvisor.digest.CodeDigest;
import org.codeAdvisorGroup.CodeAdvisor.entities.Code;
import org.codeAdvisorGroup.CodeAdvisor.entities.Comment;
import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.codeAdvisorGroup.CodeAdvisor.messages.CodeDetailReplyMessage;
import org.codeAdvisorGroup.CodeAdvisor.messages.CodeDetailRequestMessage;
import org.codeAdvisorGroup.CodeAdvisor.messages.CodeListReplyMessage;
import org.codeAdvisorGroup.CodeAdvisor.messages.CodeListRequestMessage;
import org.codeAdvisorGroup.CodeAdvisor.repositories.CodeRepository;
import org.codeAdvisorGroup.CodeAdvisor.repositories.CommentRepository;
import org.codeAdvisorGroup.CodeAdvisor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CommentRepository commentRepository;

    //Controller for initial code list
    @MessageMapping("/codelist")
    @SendToUser(value="/codelist",broadcast=false)
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true, noRollbackFor=Exception.class)
    public CodeListReplyMessage codeListQuery(CodeListRequestMessage message){
        User user=userRepository.findByUsername(message.getUsername());
        if (user==null||user.getCodeList().isEmpty()){//no code with this user as author is find
            return new CodeListReplyMessage(false,null);
        }else{
            List<Code> codeList=user.getCodeList();
            LinkedList<CodeDigest> codeDigests=new LinkedList<>();
            for(Code code:codeList){
                codeDigests.add(new CodeDigest(code.getId(),code.getTitle(),code.getUser().getUsername()));

            }
            return new CodeListReplyMessage(true, codeDigests);
        }
    }

    //Controller for initial code detail
    @MessageMapping("/codeDetail")
    @SendToUser(value="/codeDetail",broadcast=false)
    public CodeDetailReplyMessage codeDetailQuery(CodeDetailRequestMessage message){
        Optional<Code> codeOptional=codeRepository.findSafe(message.getCodeID());

        if(codeOptional.isPresent()){
            Code code=codeOptional.get();
            List<Comment> commentList=commentRepository.findSafeByCode_Id(message.getCodeID());
            return new CodeDetailReplyMessage(true,code,commentList);
        }else{
            return new CodeDetailReplyMessage(false,null,null);
        }
    }
}