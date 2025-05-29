package com.nyc.hosp.controller;

import com.nyc.hosp.domain.Role;
import com.nyc.hosp.model.HospuserDTO;
import com.nyc.hosp.repos.RoleRepository;
import com.nyc.hosp.service.HospuserService;
import com.nyc.hosp.util.CustomCollectors;
import com.nyc.hosp.util.ReferencedWarning;
import com.nyc.hosp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Controller
@RequestMapping("/hospusers")
public class HospuserController {

    private final HospuserService hospuserService;
    private final RoleRepository roleRepository;

    public HospuserController(final HospuserService hospuserService,
            final RoleRepository roleRepository) {
        this.hospuserService = hospuserService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("roleValues", roleRepository.findAll(Sort.by("roleId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Role::getRoleId, Role::getRolename)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("hospusers", hospuserService.findAll());
        return "hospuser/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("hospuser") final HospuserDTO hospuserDTO) {
        return "hospuser/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("hospuser") @Valid final HospuserDTO hospuserDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hospuser/add";
        }
        if (hospuserDTO.getUserpassword().equals(hospuserDTO.getUserpassword2())){
            hospuserDTO.setLastlogondatetime(OffsetDateTime.now());
            hospuserDTO.setLocked(false);
            hospuserDTO.setLastchangepassword((OffsetDateTime) LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC));
            hospuserService.create(hospuserDTO);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hospuser.create.success"));
            return "redirect:/hospusers";
        } else {
            bindingResult.rejectValue("userpassword2", "error.hospuser", "Password and Confirm Password do not match");
            return "hospuser/add";
        }
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final Integer userId, final Model model) {
        model.addAttribute("hospuser", hospuserService.get(userId));
        return "hospuser/edit";
    }

    @PostMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final Integer userId,
            @ModelAttribute("hospuser") @Valid final HospuserDTO hospuserDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "hospuser/edit";
        }
        hospuserService.update(userId, hospuserDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("hospuser.update.success"));
        return "redirect:/hospusers";
    }

    @PostMapping("/delete/{userId}")
    public String delete(@PathVariable(name = "userId") final Integer userId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = hospuserService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            hospuserService.delete(userId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("hospuser.delete.success"));
        }
        return "redirect:/hospusers";
    }

}
