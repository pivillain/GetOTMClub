#!/usr/bin/perl
##############################################################################
#                                                                            #
# GetOTMClub.pl                                                              #
# Recherche si le licencié marqué en formation a fait                        #
# 5 matchs ou plus et est a basculer OTM Club                                #
#                                                                            #
# Parametre :  fichier extrait de FBI V2                                     # 
#              M02_liste_designations_officiels_du_club_date_L_P_P_V .csv    #
# Version 1.0              Pierre VILLAIN 11/06/2018                         #
#                                                                            #
#                                                                            #
##############################################################################
use File::Basename;

###parametres par defaut
my $ExtractFile=$ARGV[0];
my $Dest="OTM_Club.csv";
my $ligne="";
my $OTM_NOM="nom";
my $OTM_PRENOM="";
my $OTM_CLASSE="";
my @OTM_MATCHS_DATE;
my $DateMatch5="";

#open (FICLOG,">otm.log");
	open (FICSRC,"$ExtractFile") || die "Erreur ouverture $ExtractFile";
	while(<FICSRC>)
	{
		my $Ligne=$_;
		chomp $Ligne;
#print FICLOG $Ligne."\n";
		#nom,prenom,libelle,lb_nom_abg,numero,GS1,GS2,lb_catc,excuse,dt_reelle,horaire,lb_sall
		my @champs=split /,/,$Ligne;
		if ( $champs[0] eq $OTM_NOM && $champs[1] eq $OTM_PRENOM)
		{
#print FICLOG $OTM_NOM." ".$OTM_PRENOM."==".$champs[0]." ".$champs[1];
#print FICLOG " classe = ".$champs[2]."\n";
			if ( $champs[2] =~ /OTM/)
			{
				if ($OTM_CLASSE =~ /formation/) {$OTM_CLASSE=$champs[2];}
				$champs[9] =~ /(\d{2})\/(\d{2})\/(\d{4})/;
				my $datetosort=$3.$2.$1;
				push @OTM_MATCHS_DATE,($champs[9],$datetosort);
#print FICLOG "date match $champs[9]\n";
				}
		}
		else
		{
			$DateMatch5="";
#print FICLOG "Enregistrement des do,,ées en cours \n";
			#On enregistre les données en cours
			#length($OTM_NOM) > 0 && 
			if ($OTM_NOM ne "nom")
			{
#print FICLOG "NB matchs avant sort = $#OTM_MATCHS_DATE \n";
				@OTM_MATCHS_DATE = sort {$x <=> $y} @OTM_MATCHS_DATE;
#print FICLOG "NB matchs après sort = $#OTM_MATCHS_DATE \n";
				if ($OTM_CLASSE =~ /formation/ && $#OTM_MATCHS_DATE > 4)
				{
#print FICLOG "Date match 5 = $OTM_MATCHS_DATE[4] \n";
					$OTM_MATCHS_DATE[4] =~ /(\d{4})(\d{2})(\d{2})/;
					$DateMatch5=$3.'/'.$2.'/'.$1;
				}
				if ($OTM_CLASSE =~ /OTM/)
				{
					push @Sortie,"$OTM_NOM,$OTM_PRENOM,$OTM_CLASSE,$#OTM_MATCHS_DATE,$DateMatch5";
				}
#print FICLOG "tableau sortie $OTM_NOM,$OTM_PRENOM,$OTM_CLASSE,$#OTM_MATCHS_DATE,$DateMatch5 \n";
			}
			#On charge les variables OTM
			$OTM_NOM=$champs[0];
			$OTM_PRENOM=$champs[1];
			$OTM_CLASSE=$champs[2];
			@OTM_MATCHS_DATE=();
			$champs[9] =~ /(\d{2})\/(\d{2})\/(\d{4})/;
			my $datetosort=$3.$2.$1;
			push @OTM_MATCHS_DATE,($datetosort);
		}
	}
	close FICSRC;
	#on traite la dernière ligne
	@OTM_MATCHS_DATE = sort {$x <=> $y} @OTM_MATCHS_DATE;
	if ($OTM_CLASSE =~ /formation/ && $#OTM_MATCHS_DATE > 4)
	{
		$OTM_MATCHS_DATE[4] =~ /(\d{4})(\d{2})(\d{2})/;
		$DateMatch5=$3.'/'.$2.'/'.$1;
	}
	if ($OTM_CLASSE =~ /OTM/)
	{
		push @Sortie,"$OTM_NOM,$OTM_PRENOM,$OTM_CLASSE,$#OTM_MATCHS_DATE,$DateMatch5";
	}
	#On ecrit le tableau final
#print FICLOG "ecriture finale\n";
	open (FICDEST,">$Dest") || die "Erreur ecriture $Dest";
	print FICDEST "nom,prenom,classe,Nb_matchs,date_match5\n";
	foreach (@Sortie)
	{
		print FICDEST $_."\n";
	}
	close FICDEST;
#	close FICLOG;

