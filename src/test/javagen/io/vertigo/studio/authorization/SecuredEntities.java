/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio.authorization;

import io.vertigo.account.authorization.definitions.Authorization;
import io.vertigo.account.authorization.definitions.AuthorizationName;
import io.vertigo.account.authorization.definitions.OperationName;
import io.vertigo.core.node.Node;
import io.vertigo.studio.domain.security.Record;

/**
 * Warning. This class is generated automatically !
 *
 * Enum of the security authorizations and operations associated with secured entities known by the application.
 */
public final class SecuredEntities {

	private SecuredEntities() {
		//private constructor
	}

	/**
	 * Authorizations of Record.
	 */
	public enum RecordAuthorizations implements AuthorizationName {
		/** Création attribuable par type et par montant maximum.. */
		AtzRecord$create,
		/** Suppression attribuable par type de marché ou pour le créateur tant que non Publié (Attribué sans type = régle non vérifiée).. */
		AtzRecord$delete,
		/** Notification par type de marché et dans le respect de la hierachy geographique. Overrides WRITE : donne l'opération WRITE en conservant le périmètre de l'opération NOTIFY. */
		AtzRecord$notify,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		AtzRecord$read,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		AtzRecord$read2,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		AtzRecord$read3,
		/** Visibilité globale attribuée avec une opération transverser Hors Périmétre. Sans régles = toujours ok.. */
		AtzRecord$readHp,
		/** Tests uniquement. */
		AtzRecord$test,
		/** Tests uniquement. */
		AtzRecord$test2,
		/** Tests uniquement. */
		AtzRecord$test3,
		/** Ecriture possible par l'utilisateur créateur avant que le marché ne soit Archivé, ou avec un montant max avant Archivé.. */
		AtzRecord$write;

		/**
		 * Get the associated authorization.
		 *
		 * @param code authorization code
		 * @return authorization
		 */
		public static Authorization of(final String code) {
			return Node.getNode().getDefinitionSpace().resolve(code, Authorization.class);
		}

		/**
		 * Get the associated authorization.
		 *
		 * @return role
		 */
		public Authorization getAuthorization() {
			return of(name());
		}
	}

	/**
	 * Operations of Record.
	 */
	public enum RecordOperations implements OperationName<Record> {
		/** Création attribuable par type et par montant maximum.. */
		create,
		/** Suppression attribuable par type de marché ou pour le créateur tant que non Publié (Attribué sans type = régle non vérifiée).. */
		delete,
		/** Notification par type de marché et dans le respect de la hierachy geographique. Overrides WRITE : donne l'opération WRITE en conservant le périmètre de l'opération NOTIFY. */
		notify,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		read,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		read2,
		/** Visibilité attribuable avec un montant maximum, et visibilité pour l'utilisateur créateur.. */
		read3,
		/** Visibilité globale attribuée avec une opération transverser Hors Périmétre. Sans régles = toujours ok.. */
		readHp,
		/** Tests uniquement. */
		test,
		/** Tests uniquement. */
		test2,
		/** Tests uniquement. */
		test3,
		/** Ecriture possible par l'utilisateur créateur avant que le marché ne soit Archivé, ou avec un montant max avant Archivé.. */
		write;
	}
}
