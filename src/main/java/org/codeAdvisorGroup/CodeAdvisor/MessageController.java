package org.codeAdvisorGroup.CodeAdvisor;

import org.codeAdvisorGroup.CodeAdvisor.digest.CodeDigest;
import org.codeAdvisorGroup.CodeAdvisor.entities.Code;
import org.codeAdvisorGroup.CodeAdvisor.entities.Comment;
import org.codeAdvisorGroup.CodeAdvisor.entities.User;
import org.codeAdvisorGroup.CodeAdvisor.messages.*;
import org.codeAdvisorGroup.CodeAdvisor.repositories.CodeRepository;
import org.codeAdvisorGroup.CodeAdvisor.repositories.CommentRepository;
import org.codeAdvisorGroup.CodeAdvisor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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


    @MessageMapping("/commentForCode{codeID}")
    @SendTo("/commentForCode{codeID}")
    public RealTimeCommentReplyMessage realTimeComment(RealTimeCommentRequestMessage message, @DestinationVariable String codeID, Principal principal){
        String username=principal.getName();
        User user=userRepository.findByUsername(username);
        Optional<Code> code=codeRepository.findById(Long.parseLong(codeID));
        if(user!=null&&code.isPresent()){
            Comment comment=new Comment(user,code.get(),message.getCommentToSend());
            commentRepository.save(comment);
            Comment safeComment=commentRepository.findSafeById(comment.getId());
            if(safeComment!=null){
                return new RealTimeCommentReplyMessage(true,safeComment);
            }else{
                return new RealTimeCommentReplyMessage(false,null);
            }
        }else{
            return new RealTimeCommentReplyMessage(false,null);
        }
    }

    @MessageMapping("/codeUpload/{userName}")
    @SendTo("/codeForUser/{userName}")
    public RealTimeCodeUploadReplyMessage realTimeCodeUpload(RealTimeCodeUploadRequestMessage message, @DestinationVariable String userName, Principal principal){
        String principalName=principal.getName();
        if(!userName.equals(principalName)){//someone is trying to cheat the server
            return new RealTimeCodeUploadReplyMessage(false,null);
        }
        User user=userRepository.findByUsername(principalName);
        if(user!=null){
            Code code=new Code(user,message.getCodeTitleToSend(),message.getCodeToSend());
            user.getCodeList().add(code);
            user=userRepository.save(user);
            List<Code> savedCodeList=user.getCodeList();
            if (savedCodeList!=null&&!savedCodeList.isEmpty()) {
                code=savedCodeList.get(savedCodeList.size()-1);
            }else{
                return new RealTimeCodeUploadReplyMessage(false,null);
            }
            Optional<Code> safeCode=codeRepository.findSafe(code.getId());
            if(safeCode.isPresent()){
                return new RealTimeCodeUploadReplyMessage(true,safeCode.get());
            }else{
                return new RealTimeCodeUploadReplyMessage(false,null);
            }
        }else{
            return new RealTimeCodeUploadReplyMessage(false,null);
        }
    }

}